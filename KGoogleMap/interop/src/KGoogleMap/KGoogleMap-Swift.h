#ifndef KGoogleMapWrapper_h
#define KGoogleMapWrapper_h
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import "GMSMapView.h"
#import "GooglePlaces/GMSAutocompleteViewController.h"

#if defined(__arm64__) && __arm64__
    #import "simulator64/KGoogleMap-Swift.h"
#elif defined(__x86_64__) && __x86_64__
      #import "/Users/michelleraouf/Desktop/kmm/kgoogle-map/KGoogleMap/interop/src/KGoogleMap/simulator32/KGoogleMap-Swift.h"
#else
     #import "ios/KGoogleMap-Swift.h"

#endif
#endif /* KGoogleMapWrapper_h */
