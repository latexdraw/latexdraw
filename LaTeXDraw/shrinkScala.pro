-injars			scala-library.jar
-injars			LaTeXDraw.jar
-outjars			scala-library-shrinked.jar
-libraryjars	/opt/jdk1.6.0/jre/lib/rt.jar
-libraryjars	jlibeps.jar
#-libraryjars	LaTeXDraw.jar
-libraryjars	malai.jar
-libraryjars	PDFRenderer-0.9.1.jar

-dontobfuscate
-dontoptimize

-keepattributes **

-keepclasseswithmembers public class * {
	public static void main(java.lang.String[]);
}

-keep class * implements org.xml.sax.EntityResolver

-keepclassmembers class * {
	** MODULE$;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinPool {
	long eventCount;
	int workerCounts;
	int runControl;
	scala.concurrent.forkjoin.ForkJoinPool$WaitQueueNode syncStack;
	scala.concurrent.forkjoin.ForkJoinPool$WaitQueueNode spareStack;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinWorkerThread {
	int base;
	int sp;
	int runState;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinTask {
	int status;
}

-keepclassmembernames class scala.concurrent.forkjoin.LinkedTransferQueue {
	scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference head;
	scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference tail;
	scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference cleanMe;
}


-keepnames class * implements java.io.Serializable

-dontnote

-keep class scala.runtime.** { *; }
#-keep class scala.** { *; }


-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-ignorewarnings





