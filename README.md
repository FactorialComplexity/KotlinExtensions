# KotlinExtensions

A set of Kotlin extensions for commonly used Classes in Android development.
Including such files:

* ActivityExt.kt
* BitmapExt.kt
* CommonExt.kt
* ContextExt.kt
* EditTextExt.kt
* FileExt.kt
* FragmentExt.kt
* JSONArrayExt.kt
* JSONObjectExt.kt
* LiveDataExt.kt
* StringExt.kt
* TextViewExt.kt
* UriExt.kt
* ViewExt.kt

## Usage

Copy and paste proffered extensions into your project and use it. That's all!
For example:

### Register permission callback in Fragment

```kotlin
class AFragment : Fragment() {

    // ...

    val requestPermissionCallback = registerRequestPermissionCallback {
        if (it) {
            // Permission granted
            // ...
        } else {
            // Permission denied
            // ...
        }
    }

    // ...

    fun askForCameraPermission() {
        requestPermissionCallback?.launch(android.Manifest.permission.CAMERA)
    }

    // ...
}
```

### Register permission callback in Fragment

```kotlin
// ...

fun setClickListeners(bNext: Button) {

    bNext.clickWithDebounce(debounceTime = 350L) {
        // Button clicked
        // ...
    }
}

// ...
```

