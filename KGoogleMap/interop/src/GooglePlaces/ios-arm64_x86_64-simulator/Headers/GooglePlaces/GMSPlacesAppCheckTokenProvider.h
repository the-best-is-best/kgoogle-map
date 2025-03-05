//
//  GMSPlacesAppCheckTokenProvider.h
//  Google Places SDK for iOS
//
//  Copyright 2024 Google LLC
//
//  Usage of this SDK is subject to the Google Maps/Google Earth APIs Terms of
//  Service: https://cloud.google.com/maps-platform/terms
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN
/**
 * Completion type for receiving App Check tokens. If an error occurred, `token` will be nil and
 * `error` will contain information about the error.
 *
 * @param token The `NSString` that was returned.
 * @param error The error that occurred, if any.
 */
typedef void (^GMSAppCheckTokenCompletion)(NSString *_Nullable token, NSError *_Nullable error);

/**
 * Protocol for providing App Check tokens for Places SDK.
 */
@protocol GMSPlacesAppCheckTokenProvider <NSObject>

/**
 * Fetches an App Check token.
 *
 * @param completion The `GMSAppCheckTokenCompletion` to invoke when a token is fetched.
 */
- (void)fetchAppCheckTokenWithCompletion:(GMSAppCheckTokenCompletion)completion;
@end

NS_ASSUME_NONNULL_END
