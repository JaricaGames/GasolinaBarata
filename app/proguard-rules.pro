# Reglas de R8 del proyecto.
#
# Hilt, Compose, Maps y Ads traen sus reglas automaticamente (consumer rules).
# Lo unico que hay que proteger a mano es lo que pasa por reflexion: Gson.

# --- Gson ---
# Los genericos (TypeToken<List<RecentSearch>>, Type de DailyJsonCache) necesitan
# la firma generica y las anotaciones en el bytecode
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# --- Modelos deserializados por Gson via reflexion ---
# Si R8 renombra estos campos, el parseo del JSON del Ministerio devuelve nulls EN SILENCIO
# (la app arranca pero no muestra gasolineras). No quitar.
-keep class com.jarica.preciogasolina.data.network.Retrofit.response.** { *; }
-keep class com.jarica.preciogasolina.core.RecentSearch { *; }

# Trazas de crash legibles en Play Console (el mapping.txt se sube aparte)
-keepattributes SourceFile,LineNumberTable
