//
//  GMSPlaceAuthorAttribution.h
//  Google Places SDK for iOS
//
//  Copyright 2023 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//



#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/** A class representing an author attribution. */
@interface GMSPlaceAuthorAttribution : NSObject

/** Returns the `GMSPlaceAuthorAttribution` with a name, URI, and author photoURI. */
- (instancetype)initWithName:(NSString *)name
                         URI:(nullable NSURL *)URI
                    photoURI:(nullable NSURL *)photoURI;

/** Returns the name of the author. */
@property(nonatomic, readonly, copy) NSString *name;

/** Returns the URI of the author. */
@property(nonatomic, readonly, copy, nullable) NSURL *URI;

/** Returns the profile photo URI of the author. */
@property(nonatomic, readonly, copy, nullable) NSURL *photoURI;

@end

NS_ASSUME_NONNULL_END

