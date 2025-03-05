//
//  GMSPlaceSearchByTextRequest.h
//  Google Places SDK for iOS
//
//  Copyright 2023 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//

#import <CoreLocation/CoreLocation.h>
#import <Foundation/Foundation.h>


#import "GMSPlaceLocationOptions.h"
#import "GMSPlaceProperty.h"

/**
 * \defgroup PlaceSearchByTextRankPreference GMSPlaceSearchByTextRankPreference
 * @{
 */

/** How results will be ranked in the response. */
typedef NS_ENUM(NSInteger, GMSPlaceSearchByTextRankPreference) {
  GMSPlaceSearchByTextRankPreferenceDistance,
  GMSPlaceSearchByTextRankPreferenceRelevance
};

@class GMSCoordinateBounds;

NS_ASSUME_NONNULL_BEGIN

/** Represents a text search request definition to be sent via `GMSPlacesClient`. */

@interface GMSPlaceSearchByTextRequest : NSObject

/**
 * Instantiates a `GMSPlaceSearchByTextRequest` with a text query and place properties to return.
 *
 * @param textQuery The string query for the search request.
 * @param placeProperties The array of `GMSPlaceProperty` that should be included in place results.
 * Must not be empty. An empty list will result in an error when trying to send the request.
 */
- (instancetype)initWithTextQuery:(NSString *)textQuery
                  placeProperties:(NSArray<GMSPlaceProperty> *)placeProperties
    NS_DESIGNATED_INITIALIZER;

/** Default init is not available. Please use the designated initializer. */
- (instancetype)init NS_UNAVAILABLE;

/**
 * The requested place type.
 *
 * <p>See
 * https://developers.google.com/maps/documentation/places/web-service/search-textual#supported-types
 * for the most up-to-date list of supported types available
 */
@property(nonatomic, copy, nullable) NSString *includedType;

/**
 * The maximum number of results to return. Allows values between 1 and 20.
 *
 * <p>The allowed range of this field can be found at
 * https://developers.google.com/maps/documentation/places/web-service/search-textual#maxresultcount.
 * <p>Defaults to the upper limit.
 */
@property(nonatomic) int maxResultCount;

/**
 * The minimum average user rating for places to be included in the results.
 * Rating will be within a range between 0.0 and 5.0.
 *
 * <p>The specified rating will be rounded up to the nearest 0.5. See `GMSPlace` rating property.
 * <p>Defaults to 0.0.
 */
@property(nonatomic) float minRating;

/**
 * Filter results to places that are currently open.
 * <p>Defaults to true.
 */
@property(nonatomic) BOOL isOpenNow;

/** The array of `GMSPlaceProperty` that should be included in place results. */
@property(nonatomic, readonly) NSArray<GMSPlaceProperty> *placeProperties;

/**
 * An Array of the `GMSPlacesPriceLevel` values that should filter the places included
 * in the results.
 *
 * <p>Any combination of price levels can be selected; default is
 * all price levels.
 */
@property(nonatomic, nullable) NSArray<NSNumber *> *priceLevels;

/** The `GMSPlaceSearchByTextRankPreference` used to sort the results returned from the request. */
@property(nonatomic) GMSPlaceSearchByTextRankPreference rankPreference;

/**
 * The unicode country/region code (CLDR) of the location where the request is coming from.
 *
 * <p>The region may affect the format and type of data returned. If no code is provided, there is
 * no bias towards any country/region.
 *
 * <p>See https://unicode.org/cldr/charts/latest/supplemental/territory_containment_un_m_49.html
 * for region codes.
 */
@property(nonatomic, copy, nullable) NSString *regionCode;

/**
 * Defines whether only results of `includedType`
 * should be returned.
 *
 * <p>Default value is false.
 */
@property(nonatomic) BOOL isStrictTypeFiltering;

/** The text query for the search. */
@property(nonatomic, readonly, copy) NSString *textQuery;

/**
 * The biased region to search.
 *
 * <p>This location serves as a bias, which means there is a preference for results in the
 * given location.
 *
 * <p>A locationRestriction or a locationBias must be set when performing a search request.
 * <p>If set along with locationRestriction, this will be ignored.
 */
@property(nonatomic, nullable) id<GMSPlaceLocationBias> locationBias;

/**
 * The restricted region to search.
 *
 * <p>This location serves as a restriction which means results outside the given location will not
 * be returned.
 *
 * <p>A locationRestriction or a locationBias must be set when performing a search request.
 * <p>Cannot be used with locationBias.
 * <p>The locationRestriction for text search only supports `GMSPlaceRectangularLocationOption`.
 */
@property(nonatomic, nullable) id<GMSPlaceLocationRestriction> locationRestriction;

@end

NS_ASSUME_NONNULL_END
