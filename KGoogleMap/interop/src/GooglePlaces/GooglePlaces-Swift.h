#if TARGET_OS_SIMULATOR  // For iOS simulator architecture (x86_64 or arm64)
#import "/Users/michelleraouf/Desktop/kmm/kgoogle-map/KGoogleMap/interop/src/GooglePlaces/ios-arm_x86_64-simulator/Headers/GooglePlaces/GooglePlaces.h"
#elif TARGET_CPU_ARM64  // For real iOS device architecture (arm64)
#import "/Users/michelleraouf/Desktop/kmm/kgoogle-map/KGoogleMap/interop/src/GooglePlaces/ios-arm64/Headers/GooglePlaces/GooglePlaces.h"
#else  // Fallback case for other platforms (e.g., iOS 32-bit, etc.)
#import "/Users/michelleraouf/Desktop/kmm/kgoogle-map/KGoogleMap/interop/src/GooglePlaces/ios-arm64/Headers/GooglePlaces/GooglePlaces.h"
#endif
