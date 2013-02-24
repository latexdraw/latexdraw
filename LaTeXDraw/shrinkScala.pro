-injars			scala-library-2.9.2.jar
-injars			LaTeXDraw.jar
-outjars		scala-library-shrinked.jar
-libraryjars	/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/rt.jar
-libraryjars	jlibeps.jar
#-libraryjars	LaTeXDraw.jar
-libraryjars	PDFRenderer-0.9.1.jar
-libraryjars	malai.swing-0.2-SNAPSHOT.jar
-libraryjars	malai.core-0.2-SNAPSHOT.jar

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





