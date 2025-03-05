//
//  GMSAutocompleteRequest.h
//  Google Places SDK for iOS
//
//  Copyright 2024 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//

#import <Foundation/Foundation.h>


@class GMSAutocompleteSessionToken;
@class GMSAutocompleteFilter;

NS_ASSUME_NONNULL_BEGIN

/** The request for autocomplete data. */
@interface GMSAutocompleteRequest : NSObject

/** The partial text to autocomplete. */
@property(nonatomic, copy) NSString *query;

/** The `GMSAutocompleteSessionToken` to associate requests to a billing session. */
@property(nonatomic, nullable) GMSAutocompleteSessionToken *sessionToken;

/** The `GMSAutocompleteFilter` to apply to the results. */
@property(nonatomic, nullable) GMSAutocompleteFilter *filter;

/** Request initializer.
 *
 * @param query The query required for the request.
 */
- (instancetype)initWithQuery:(NSString *)query NS_DESIGNATED_INITIALIZER;

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
