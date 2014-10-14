package net.sf.latexdraw.glib.views.Java2D.impl

import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.RandomAccessFile
import java.io.StringWriter
import java.nio.channels.FileChannel
import java.util.regex.Pattern

import scala.collection.mutable.HashMap
import scala.collection.mutable.Map
import scala.collection.mutable.Set

import com.sun.pdfview.PDFFile

import javax.swing.SwingUtilities
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.filters.PDFFilter
import net.sf.latexdraw.filters.TeXFilter
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.interfaces.shape.IText
import net.sf.latexdraw.glib.ui.ICanvas
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewText
import net.sf.latexdraw.glib.views.latex.DviPsColors
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator
import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import net.sf.latexdraw.util.LFileUtils
import net.sf.latexdraw.util.LNumber
import net.sf.latexdraw.util.LResources
import net.sf.latexdraw.util.LSystem
import net.sf.latexdraw.util.StreamExecReader

/**
 * This flyweight manages the thumbnails of the text shapes. Its goal is to limit the number
 * of pictures compiled and kept in memory<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2013-02-28<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
object FlyweightThumbnail {
	/**
	 * The stored images. To obtain an image, its corresponding text must be given.
	 * The integer associated with the Image corresponds to the text objects
	 * using this image. When this number of objects equals 0, the image is removed and flushed.
	 * The second String is the path of the files: for instance on Unix is can be
	 * /tmp/latexdraw180980 (without any extension). The last String is the log of the
	 * compilation.
	 */
	val images : Map[String, (Image, Set[IViewText], String, String)] = new HashMap[String, (Image, Set[IViewText], String, String)]()

	val _scaleImage = 2.0

	val inProgressMsg = "Creation in progress"

	val creationsInProgress : Set[IViewText] = Set[IViewText]()

	var _canvas : ICanvas = null

	var _thread = true

	/** True: the creation will be performed using threads. */
	def setThread(withThread:Boolean) {  _thread = withThread }

	/** Sets the canvas to notify when a picture is created (with threads only). */
	def setCanvas(c:ICanvas) { _canvas = c }

	/** A ratio used to create bigger thumbnails to improve the quality of the displayed image. */
	def scaleImage() = _scaleImage


	def clear() {
		creationsInProgress.synchronized{creationsInProgress.clear}
		images.synchronized{
			images.foreach{tu => flushImage(tu._2._1, tu._2._3)}
			images.clear
		}
	}

	/**
	 * Returns the image corresponding to the given text. If the image does not already exists,
	 * it is created and stored.
	 */
	def getImage(shape:IViewText) : Image = if(shape==null) null else getImageInfo(shape)._1


	/**
	 * Returns the log corresponding to the compilation of the given text. If the image does not already exists,
	 * it is created and stored.
	 */
	def getLog(shape:IViewText) : String = if(shape==null) "" else getImageInfo(shape)._4


	/**
	 * Returns some information corresponding to the the given text. If the image does not already exists,
	 * it is created and stored.
	 */
	def getImageInfo(view:IViewText) : (Image, Set[IViewText], String, String) = {
		val shape = view.getShape.asInstanceOf[IText]
		var res : (Image, Set[IViewText], String, String) = null

		if(creationsInProgress.synchronized{creationsInProgress.contains(view)})
			res = new Tuple4[Image,Set[IViewText],String,String](null, Set(), "", inProgressMsg)
		else {
			val text = shape.getText
			images.synchronized{images.get(text)} match {
			case Some(tuple) =>
				tuple._2.synchronized { tuple._2+=view }
				res = new Tuple4[Image,Set[IViewText],String,String](tuple._1, tuple._2, tuple._3, tuple._4)
				images.synchronized{images+=(text -> res)}
			case _ =>
				if(_thread) {
					creationsInProgress.synchronized{creationsInProgress+=view}
					res = new Tuple4[Image,Set[IViewText],String,String](null, Set(), "", "Creation in progress")
					SwingUtilities.invokeLater(new Thread() {
						override def run() {
							val tuple = createImage(shape)
							images.synchronized{images+=(text -> new Tuple4[Image,Set[IViewText],String,String](tuple._1, Set(view), tuple._2, tuple._3))}
							creationsInProgress.synchronized{creationsInProgress-=view}
							view.updateBorder
							if(_canvas!=null) {
								_canvas.getBorderInstrument.update
								_canvas.refresh
							}
						}
					})
        }
				else {
					val tuple = createImage(shape)
					res = new Tuple4[Image,Set[IViewText],String,String](tuple._1, Set(view), tuple._2, tuple._3)
					images+=(text -> res)
				}
			}
		}
		res
	}


	/**
	 * When a text picture is flushed, it must notified this flyweight that it has to check if the
	 * corresponding image must be flushed as well.
	 */
	def notifyImageFlushed(view:IViewText, text:String) {
		if(!creationsInProgress.synchronized{creationsInProgress.contains(view)}) {
			images.synchronized{images.get(text)} match {
				case Some(tuple) =>
					tuple._2.synchronized{ tuple._2-=view }
					if(tuple._2.isEmpty) { // No more used, so flushed.
						images.synchronized{images.remove(text)}
						flushImage(tuple._1, tuple._3)
					}else // Decreasing the number of objets using this image.
						images.synchronized{images+=(text -> new Tuple4[Image,Set[IViewText],String,String](tuple._1, tuple._2, tuple._3, tuple._4))}
				case _ =>
			}
		}
	}


	/**
	 * Flushes the pictures of the text and all the related resources.
	 * @since 3.0
	 */
	private def flushImage(image:Image, pathPic:String) {
		if(image!=null)
		// Flushing the picture.
			image.flush

		// Removing the picture file.
		val file = new File(pathPic)
		if(file.exists && file.canWrite)
			file.delete
	}


	private def getLaTeXDocument(shape:IText) : String = {
		val code = shape.getText
		val doc = new StringBuilder()
		val textColour = shape.getLineColour
		var coloured = false

		// We must scale the text to fit its latex size: latexdrawDPI/latexDPI is the ratio to scale the
		// created png picture.
		val scale = IShape.PPC*PSTricksConstants.INCH_VAL_CM/PSTricksConstants.INCH_VAL_PT*_scaleImage

		doc.append("\\documentclass{standalone}\n\\usepackage[usenames,dvipsnames]{pstricks}") //$NON-NLS-1$
		doc.append(LaTeXGenerator.getPackages).append('\n')
		doc.append("\\begin{document}\n\\psscalebox{") //$NON-NLS-1$
		doc.append(LNumber.getCutNumber(scale).toFloat).append(' ')
		doc.append(LNumber.getCutNumber(scale).toFloat).append('}').append('{')

		if(!textColour.equals(PSTricksConstants.DEFAULT_LINE_COLOR)) {
			var name = DviPsColors.INSTANCE.getColourName(textColour)
			coloured = true

			if(!name.isPresent)
				name = DviPsColors.INSTANCE.addUserColour(textColour)

			val str = name.orElse("colorNotFound")
			doc.append(DviPsColors.INSTANCE.getUsercolourCode(str)).append("\\textcolor{").append(str).append('}').append('{') //$NON-NLS-1$
		}

		doc.append(code)

		if(coloured)
			doc.append('}')

		doc.append("}\n\\end{document}") //$NON-NLS-1$
		doc.toString
	}



	/**
	 * @return The precise latex error messages that the latex compilation produced.
	 * @since 3.0
	 */
	def getLatexErrorMessageFromLog(shape:IText) : String = {
		val log = images.get(shape.getText) match {
			case Some(elt) => elt._4
			case _ => ""
		}
		val matcher = Pattern.compile(".*\r?\n").matcher(log) //$NON-NLS-1$
		val errors 	= new StringBuilder()
		var line : String = null

		while(matcher.find) {
			line = matcher.group

			if(line.startsWith("!")) { //$NON-NLS-1$
				errors.append(line.substring(2, line.length))
				var ok = true
				while(ok && matcher.find) {
					line = matcher.group

					if(line.startsWith("l.")) //$NON-NLS-1$
						ok = false
          else
						errors.append(LResources.EOL).append(line).append(LResources.EOL)
				}
			}
		}
		errors.toString
	}



	/**
	 * Executes a given command and returns the log.
	 * @param cmd The command to execute.
	 * @return True if the command exit normally and the log.
	 * @since 3.0
	 */
	private def execute(cmd:Array[String]) : (Boolean, String) = {
		var log = ""
		try {
			val process = Runtime.getRuntime.exec(cmd)
			val errReader = new StreamExecReader(process.getErrorStream)
			val outReader = new StreamExecReader(process.getInputStream)

			errReader.start
			outReader.start

			if(process.waitFor==0)
				return new Tuple2(true, log)

			log = outReader.getLog + LResources.EOL + errReader.getLog
		}catch{ case ex: Throwable => log += ex.getMessage }
		return new Tuple2(true, log)
	}



	/**
	 * @return The LaTeX compiled picture of the text with its file path and its log, or None.
	 * @since 3.0
	 */
	private def createImage(shape:IText) : (Image, String, String) = {
		var bi : Image = null
		var log = "" //$NON-NLS-1$
		val tmpDir = LFileUtils.INSTANCE.createTempDir
		val doc	= getLaTeXDocument(shape)
		val pathPic	= tmpDir.getAbsolutePath + LResources.FILE_SEP + "latexdrawTmpPic" + System.currentTimeMillis //$NON-NLS-1$
		val pathTex = pathPic + TeXFilter.TEX_EXTENSION
		val os = LSystem.INSTANCE.getSystem

		try {
			val fos	= new FileOutputStream(pathTex)
			val osw = new OutputStreamWriter(fos)
			osw.append(doc)
			osw.flush
			osw.close
			fos.flush
			fos.close

			var res = execute(Array(os.getLatexBinPath, "--halt-on-error", "--interaction=nonstopmode", "--output-directory=" + tmpDir.getAbsolutePath, pathTex)) //$NON-NLS-1$ //$NON-NLS-2$
			var ok = res._1
			log = res._2

			new File(pathTex).delete
			new File(pathPic + ".aux").delete //$NON-NLS-1$
			new File(pathPic + ".log").delete //$NON-NLS-1$
			val psExt = ".eps" //$NON-NLS-1$

			if(ok) {
				res = execute(Array(os.getDvipsBinPath, pathPic + ".dvi",  "-o", pathPic + psExt)) //$NON-NLS-1$ //$NON-NLS-2$
				ok = res._1
				log = log + res._2
				new File(pathPic + ".dvi").delete //$NON-NLS-1$
			}
			if(ok) {
				res = execute(Array(os.getPs2pdfBinPath, pathPic + psExt, pathPic + PDFFilter.PDF_EXTENSION)) //$NON-NLS-1$
				new File(pathPic + psExt).delete //$NON-NLS-1$
				ok = res._1
				log = log + res._2
			}

			if(ok)
				try {
					val file = new File(pathPic+PDFFilter.PDF_EXTENSION)
					val raf = new RandomAccessFile(file, "r")
					val fc = raf.getChannel
					val mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size)
					val pdfFile = new PDFFile(mbb)
					mbb.clear
					fc.close
					raf.close

					if(pdfFile.getNumPages==1) {
						val page = pdfFile.getPage(0)
						val bound = page.getBBox
					    val img	= page.getImage(bound.getWidth.toInt, bound.getHeight.toInt, bound, null, false, true)

					    img match {
                case bi1: BufferedImage => bi = bi1
                case _ =>
              }

					    if(img!=null)
					    	img.flush
					}
					else BadaboomCollector.INSTANCE.add(new IllegalArgumentException("Not a single page: " + pdfFile.getNumPages))
					file.delete
				}catch { case ex: Throwable => BadaboomCollector.INSTANCE.add(ex) }
		}
		catch { case e: Throwable =>
			val sw = new StringWriter()
		    val pw = new PrintWriter(sw)

		    e.printStackTrace(pw)
			new File(pathPic + TeXFilter.TEX_EXTENSION).delete
			new File(pathPic + PDFFilter.PDF_EXTENSION).delete
			new File(pathPic + ".ps").delete //$NON-NLS-1$
			new File(pathPic + ".dvi").delete //$NON-NLS-1$
			new File(pathPic + ".aux").delete //$NON-NLS-1$
			new File(pathPic + ".log").delete //$NON-NLS-1$
			BadaboomCollector.INSTANCE.add(new FileNotFoundException("Log:\n" + log + "\nException:\n" + sw))
			pw.flush
			pw.close
			sw.flush
			sw.close
		}

		return new Tuple3(bi, pathPic, log)
	}
}
