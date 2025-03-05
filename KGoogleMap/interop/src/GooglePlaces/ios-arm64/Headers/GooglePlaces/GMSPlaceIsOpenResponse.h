//
//  GMSPlaceIsOpenResponse.h
//  Google Places SDK for iOS
//
//  Copyright 2024 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//
#import <Foundation/Foundation.h>
#import "GMSPlace.h"

NS_ASSUME_NONNULL_BEGIN

/** The response object for the `isOpenWithRequest:callback:` method. */
@interface GMSPlaceIsOpenResponse : NSObject

/** Default init is not available. Please use the designated initializer. */
- (instancetype)init NS_UNAVAILABLE;

/** Initializes the response with a given status. This is meant to be used for unit testing
 * purposes. */
- (instancetype)initWithStatus:(GMSPlaceOpenStatus)status NS_DESIGNATED_INITIALIZER;

/** The open status of the place. */
@property(readonly, nonatomic) GMSPlaceOpenStatus status;

@end

NS_ASSUME_NONNULL_END
