//
//  GMSAutocompleteSuggestion.h
//  Google Places SDK for iOS
//
//  Copyright 2024 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//

#import <Foundation/Foundation.h>

#import "GMSAutocompletePlaceSuggestion.h"

NS_ASSUME_NONNULL_BEGIN

/** This class represents a suggestion of a full query based on a partially typed string. */
@interface GMSAutocompleteSuggestion : NSObject

/** `GMSAutocompletePlaceSuggestion` based on a partially typed string. */
@property(nonatomic, nullable) GMSAutocompletePlaceSuggestion *placeSuggestion;

@end

NS_ASSUME_NONNULL_END
