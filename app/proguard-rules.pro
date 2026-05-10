# Keep generic signature of Call, Response (R8 full mode strips signatures).
-keepattributes Signature, InnerClasses, EnclosingMethod, *Annotation*

# Keep kotlinx-serialization @Serializable classes and their companion serializer accessors.
-keepclasseswithmembers class **$$serializer { *; }
-keepclassmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,allowobfuscation,allowshrinking class kotlinx.serialization.KSerializer
-keep,allowobfuscation,allowshrinking interface kotlinx.serialization.KSerializer
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Retrofit / OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# Hilt
-keepnames @dagger.hilt.android.HiltAndroidApp class *
-keepnames @dagger.hilt.android.AndroidEntryPoint class *

# Coroutines (kept by R8 by default but documented here for grep-ability)
-keepclassmembers class kotlinx.coroutines.** { volatile <fields>; }

# Compose runtime reflection helpers
-keep class androidx.compose.runtime.** { *; }
