//
//  GMSPlaceProperty.h
//  Google Places SDK for iOS
//
//  Copyright 2018 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//

#import <Foundation/Foundation.h>


NS_ASSUME_NONNULL_BEGIN

/**
 * \defgroup PlaceField GMSPlaceProperty
 * @{
 */

/**
 * The properties represent individual information that can be requested for `GMSPlace` objects.
 * If no request properties are set, the `GMSPlace` object will be empty with no useful information.
 *
 * Note: `GMSPlacePropertyPhoneNumber`, `GMSPlacePropertyWebsite`, and
 * `GMSPlacePropertyAddressComponents` are not supported for `GMSPlaceLikelihoodList` place objects.
 * Refer to https://developers.google.com/places/ios-sdk/place-data-fields for more details.
 */

typedef NSString *const GMSPlaceProperty NS_TYPED_EXTENSIBLE_ENUM;

/**
 * Returns an array of all available `GMSPlaceProperty`.
 */
FOUNDATION_EXTERN NSArray<GMSPlaceProperty> *GMSPlacePropertyArray(void);

FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyName;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyPlaceID;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyPlusCode;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyCoordinate;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyOpeningHours;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyPhoneNumber;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyFormattedAddress;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyRating;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyPriceLevel;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyTypes;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyWebsite;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyViewport;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyAddressComponents;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyPhotos;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyUserRatingsTotal;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyUTCOffsetMinutes;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyBusinessStatus;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyIconImageURL;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyIconBackgroundColor;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyTakeout;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyDelivery;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyDineIn;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyCurbsidePickup;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyReservable;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyServesBreakfast;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyServesLunch;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyServesDinner;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyServesBeer;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyServesWine;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyServesBrunch;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyServesVegetarianFood;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyWheelchairAccessibleEntrance;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyCurrentOpeningHours;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertySecondaryOpeningHours;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyEditorialSummary;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyReviews;
FOUNDATION_EXTERN GMSPlaceProperty GMSPlacePropertyAll;

NS_ASSUME_NONNULL_END

