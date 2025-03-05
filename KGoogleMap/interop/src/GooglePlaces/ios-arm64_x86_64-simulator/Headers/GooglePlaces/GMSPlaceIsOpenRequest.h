//
//  GMSPlaceIsOpenRequest.h
//  Google Places SDK for iOS
//
//  Copyright 2024 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//
#import <Foundation/Foundation.h>

@class GMSPlace;

NS_ASSUME_NONNULL_BEGIN

/** Represents an is open request definition to be sent via `GMSPlacesClient`. */
@interface GMSPlaceIsOpenRequest : NSObject

/**
 * Initializes the request with a `GMSPlace` and date.
 *
 * @param place The `GMSPlace` to be used for the request.
 * @param date The date to be used for the request. If no date is provided, the current date and
 * time is used.
 */
- (instancetype)initWithPlace:(GMSPlace *)place
                         date:(nullable NSDate *)date NS_DESIGNATED_INITIALIZER;

/**
 * Initializes the request with a placeID and date.
 *
 * @param placeID The placeID to be used for the request.
 * @param date The date to be used for the request. If no date is provided, the current date and
 * time is used.
 */
- (instancetype)initWithPlaceID:(NSString *)placeID date:(nullable NSDate *)date;

/** Default init is not available. Please use the designated initializer. */
- (instancetype)init NS_UNAVAILABLE;

/** The `GMSPlace` to be used for the request. */
@property(readonly, nonatomic, nullable) GMSPlace *place;

/** The placeID to be used for the request. */
@property(readonly, nonatomic, copy, nullable) NSString *placeID;

/** The date to be used for the request. If no date is provided, the current date and time is used.
 */
@property(readonly, nonatomic, nullable) NSDate *date;

@end

NS_ASSUME_NONNULL_END
