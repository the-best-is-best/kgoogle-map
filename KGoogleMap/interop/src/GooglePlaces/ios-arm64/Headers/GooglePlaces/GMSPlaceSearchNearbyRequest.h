//
//  GMSPlaceSearchNearbyRequest.h
//  Google Places SDK for iOS
//
//  Copyright 2024 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//
#import <CoreLocation/CoreLocation.h>
#import <Foundation/Foundation.h>
#import "GMSPlaceLocationOptions.h"
#import "GMSPlaceProperty.h"

/**
 * \defgroup PlaceSearchNearbyRankPreference GMSPlaceSearchNearbyRankPreference
 * @{
 */

/** How results will be ranked in the response. */
typedef NS_ENUM(NSInteger, GMSPlaceSearchNearbyRankPreference) {
  /** (default) Sorts results based on their popularity. */
  GMSPlaceSearchNearbyRankPreferencePopularity,
  /** Sorts results in ascending order by their distance from the specified location. */
  GMSPlaceSearchNearbyRankPreferenceDistance,
};

NS_ASSUME_NONNULL_BEGIN

/** Request object to use with `GMSPlacesClient` to fetch nearby places. */
@interface GMSPlaceSearchNearbyRequest : NSObject

/**
 * Instantiates a `GMSPlaceSearchNearbyRequest` with a location restriction and place properties to
 * return.
 *
 * @param locationRestriction The region to search specified as a circle, defined by center point
 * and radius in meters.
 * @param placeProperties The array of `GMSPlaceProperty` that should be included in place results.
 * Must not be empty. An empty list will result in an error when trying to send the request.
 */
- (instancetype)initWithLocationRestriction:(id<GMSPlaceLocationRestriction>)locationRestriction
                            placeProperties:(NSArray<GMSPlaceProperty> *)placeProperties
    NS_DESIGNATED_INITIALIZER;

/** Default init is not available. Please use the designated initializer. */
- (instancetype)init NS_UNAVAILABLE;

/**
 * The region to search specified as a circle, defined by center point and radius in meters. The
 * radius must be between 0.0 and 50000.0, inclusive. Breaking this restriction will result in an
 * INVALID_ARGUMENT error. The locationRestriction must be specified with
 * `GMSPlaceCircularLocationOption`. Using `GMSPlaceRectangularLocationOption` will result in an
 * INVALID_REQUEST error.
 */
@property(nonatomic, copy) id<GMSPlaceLocationRestriction> locationRestriction;

/**
 * The array of `GMSPlaceProperty` that should be included in place results. Must not be empty. An
 * empty list will result in an error when trying to send the request.
 */
@property(nonatomic, copy) NSArray<GMSPlaceProperty> *placeProperties;

/**
 * The array of place types to include in a search. Up to 50 types can be specified. If a request is
 * specified with multiple type restrictions, only places that satisfy all of the restrictions are
 * returned. If the array is empty, then all places of all types are returned. See Table A for a
 * list of supported types
 * https://developers.google.com/maps/documentation/places/web-service/place-types#table-a.
 */
@property(nonatomic, copy, nullable) NSArray<NSString *> *includedTypes;

/**
 * The array of place types to exclude from a search. Up to 50 types can be specified. If a request
 * is specified with multiple type restrictions, only places that satisfy all of the restrictions
 * are returned.  If conflicting types are specified (e.g., types appearing in includedTypes and
 * excludedTypes), an INVALID_REQUEST error is returned. See Table A for a list of supported types
 * https://developers.google.com/maps/documentation/places/web-service/place-types#table-a. */
@property(nonatomic, copy, nullable) NSArray<NSString *> *excludedTypes;

/**
 * The array of primary place types to include in a search. Up to 50 types can be specified. If a
 * request is specified with multiple type restrictions, only places that satisfy all of the
 * restrictions are returned. For more information about primary types see:
 * https://developers.google.com/maps/documentation/places/web-service/nearby-search#includedtypesexcludedtypes,-includedprimarytypesexcludedprimarytypes
 */
@property(nonatomic, copy, nullable) NSArray<NSString *> *includedPrimaryTypes;

/**
 * The array of primary places types to exclude from a search. If conflicting types are specified
 * (e.g., types appearing in includedTypes and excludedTypes), an INVALID_REQUEST error is returned.
 * Up to 50 types can be specified. If a request is specified with multiple type restrictions, only
 * places that satisfy all of the restrictions are returned. For more information about primary
 * types see:
 * https://developers.google.com/maps/documentation/places/web-service/nearby-search#includedtypesexcludedtypes,-includedprimarytypesexcludedprimarytypes
 */
@property(nonatomic, copy, nullable) NSArray<NSString *> *excludedPrimaryTypes;

/**
 * The maximum number of `GMSPlace` results to return. Must be between 1 and 20 (default)
 * inclusive. Breaking this restriction will result in an error returned. */
@property(nonatomic) NSInteger maxResultCount;

/**
 * The unicode country/region code (CLDR) of the location where the request is coming from.
 * See https://www.unicode.org/cldr/charts/44/supplemental/territory_language_information.html for
 * region codes.
 */
@property(nonatomic, copy, nullable) NSString *regionCode;

/**
 * The `GMSPlaceSearchNearbyRankPreference` used to sort the results returned from the request.
 * The default value is `GMSPlaceSearchNearbyRankPopularity`.
 */
@property(nonatomic) GMSPlaceSearchNearbyRankPreference rankPreference;

@end

NS_ASSUME_NONNULL_END

