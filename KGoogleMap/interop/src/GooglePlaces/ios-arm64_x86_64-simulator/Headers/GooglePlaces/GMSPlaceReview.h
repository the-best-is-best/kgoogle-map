//
//  GMSPlaceReview.h
//  Google Places SDK for iOS
//
//  Copyright 2023 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//


#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@class GMSPlaceAuthorAttribution;

/** This class represents a single place review. */
@interface GMSPlaceReview : NSObject

/**
 * Instantiates a `GMSPlaceReview` with detail properties.
 *
 * @param publishDate The `NSDate` the review was published.
 * @param relativePublishDateDescription The description of the publish date relative to the time of
 * the request.
 * @param text The localized text of the review.
 * @param textLanguageCode The language code of the localized review text.
 * @param originalText The review text in its original language.
 * @param originalTextLanguageCode The language code the review was originally written in.
 * @param rating The 0.0 - 5.0 rating associated with the review.
 * @param authorAttribution The `GMSPlaceAuthorAttribution` of the review's author.
 */
- (instancetype)initWithPublishDate:(NSDate *)publishDate
     relativePublishDateDescription:(nullable NSString *)relativePublishDateDescription
                               text:(nullable NSString *)text
                   textLanguageCode:(nullable NSString *)textLanguageCode
                       originalText:(nullable NSString *)originalText
           originalTextLanguageCode:(nullable NSString *)originalTextLanguageCode
                             rating:(float)rating
                  authorAttribution:(nullable GMSPlaceAuthorAttribution *)authorAttribution;

/** The `NSDate` the review was published. */
@property(nonatomic, copy, readonly) NSDate *publishDate;

/**
 * A formatted string expressing the review date/time to the current time. Specific to the language
 * and country (e.g. "6 months ago").
 */

@property(nonatomic, copy, readonly, nullable) NSString *relativePublishDateDescription;

/** The text of the review. */
@property(nonatomic, copy, readonly, nullable) NSString *text;

/** The language code of the text of the review. */
@property(nonatomic, copy, readonly, nullable) NSString *textLanguageCode;

/** The text of the review in its original language. */
@property(nonatomic, copy, readonly, nullable) NSString *originalText;

/** The language code of the original text of the review. */
@property(nonatomic, copy, readonly, nullable) NSString *originalTextLanguageCode;

/** A whole number between 1.0 and 5.0, a.k.a. the number of stars. */
@property(nonatomic, readonly, assign) double rating;

/**
 * The attribution that must be shown to the user if this review is displayed.
 *
 * <p>See <a
 * href="https://developers.google.com/maps/documentation/places/ios-sdk/policies#other_attribution_requirements">Other
 * Attribution Requirements</a> for more details.
 */
@property(nonatomic, readonly, nullable) GMSPlaceAuthorAttribution *authorAttribution;

@end

NS_ASSUME_NONNULL_END

