#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(IosFilesAppSave, NSObject)

RCT_EXTERN_METHOD(multiply:(float)a withB:(float)b
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

RCT_EXTERN_METHOD(startDownload:(NSString *)stringValue resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject)

@end
