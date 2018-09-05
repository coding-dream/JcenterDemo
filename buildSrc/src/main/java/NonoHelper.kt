/**
 * Created by wl on 2018/9/5.
 */
import org.gradle.api.Project

val plugin_application = "com.android.application"
val plugin_library = "com.android.library"

private var sourceManifest = "src/main/AndroidManifest.xml"
private var plugin_type = plugin_application

var isModule = false

fun applyType(project: Project, type: String){
    isModule = type == plugin_application
    handleModule(project)
}

fun handleModule(it: Project) {
    if (isModule) {
        sourceManifest = "src/main/AndroidManifest.xml"
        plugin_type = plugin_application
    } else {
        sourceManifest = "src/main/AndroidManifest_lib.xml"
        plugin_type = plugin_library
    }
}
