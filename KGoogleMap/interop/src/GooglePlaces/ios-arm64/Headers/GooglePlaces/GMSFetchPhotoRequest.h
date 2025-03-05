//
//  GMSFetchPhotoRequest.h
//  Google Places SDK for iOS
//
//  Copyright 2023 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//


#import <Foundation/Foundation.h>

@class GMSPlacePhotoMetadata;

NS_ASSUME_NONNULL_BEGIN

/** Request object to use with `GMSPlacesClient` to fetch a photo. */
@interface GMSFetchPhotoRequest : NSObject

/**
 * Request object to use with `GMSPlacesClient` to fetch a photo.
 *
 * @param photoMetadata `GMSPlacePhotoMetadata` The maximum desired size in pixels
 * of the image returned by the Place Photos service.
 * @param maxSize `CGSize` The photo metadata to request.
 * If the image is smaller than the values specified, the original image will be returned. If the
 * image is larger in either dimension, it will be scaled to match the smaller of the two
 * dimensions, restricted to its original aspect ratio. CGSize height and width should be set to an
 * integer between 1 and 4800. If not, the `GMSPlacesClient` request callback will return an error.
 */

- (instancetype)initWithPhotoMetadata:(GMSPlacePhotoMetadata *)photoMetadata
                              maxSize:(CGSize)maxSize NS_DESIGNATED_INITIALIZER;

/** Default init is not available. Please use the designated initializer. */
- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
