//
//  GMSFetchPlaceRequest.h
//  Google Places SDK for iOS
//
//  Copyright 2023 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//
#import <Foundation/Foundation.h>


#import "GMSPlaceProperty.h"

NS_ASSUME_NONNULL_BEGIN

@class GMSAutocompleteSessionToken;

/** Request object to use with `GMSPlacesClient` to fetch a place. */
@interface GMSFetchPlaceRequest : NSObject

/**
 * Request object to use with `GMSPlacesClient` to fetch a place.
 *
 * @param placeID The ID of the place to be requested.
 * @param placeProperties The properties of the place to be requested. Must not be empty. An empty
 * list will result in an error when trying to send the request. Read more about <a
 * href="https://developers.google.com/maps/documentation/places/ios-sdk/place-data-fields">Place
 * Data Fields</a>.
 * @param sessionToken The `GMSAutocompleteSessionToken` to associate request to a billing session.
 */
- (instancetype)initWithPlaceID:(NSString *)placeID
                placeProperties:(NSArray<GMSPlaceProperty> *)placeProperties
                   sessionToken:(nullable GMSAutocompleteSessionToken *)sessionToken
    NS_DESIGNATED_INITIALIZER;

/** Default init is not available. Please use the designated initializer. */
- (instancetype)init NS_UNAVAILABLE;

/** Place ID of the place being requested. */
@property(nonatomic, copy, readonly) NSString *placeID;

/**
 * Place properties to include in the `GMSPlace` response. Must not be empty. An empty list will
 * result in an error when trying to send the request.
 */
@property(nonatomic, copy, readonly) NSArray<GMSPlaceProperty> *placeProperties;

/** Session token to associate request to a billing session. */
@property(nonatomic, copy, readonly, nullable) GMSAutocompleteSessionToken *sessionToken;

@end

NS_ASSUME_NONNULL_END

