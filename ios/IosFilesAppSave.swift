@objc(IosFilesAppSave)
class IosFilesAppSave: NSObject {

@objc
func startDownload(_ urlString: String, customFileName: String, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
    var parameters: [String: Any] = ["message" : ""]
    let fileName = ""
    guard let url = URL(string: urlString) else {
        parameters["success"] = false
        parameters["message"] = "Invalid URL"
        let error = NSError(domain: "", code: 0, userInfo: parameters)
        reject("error_code", "Error message", error)
        return
    }

    let task = URLSession.shared.dataTask(with: url) { (data, response, error) in
        if let error = error {
            parameters["success"] = false
            parameters["message"] = "Error downloading file: \(error)"
            let errorObj = NSError(domain: "", code: 0, userInfo: parameters)
            reject("error_code", "Error message", errorObj)
            return
        }

        guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
            parameters["success"] = false
            parameters["message"] = "Invalid status code"
            let errorObj = NSError(domain: "", code: 0, userInfo: parameters)
            reject("error_code", "Error message", errorObj)
            return
        }

        guard let data = data else {
            parameters["success"] = false
            parameters["message"] = "No data downloaded"
            let errorObj = NSError(domain: "", code: 0, userInfo: parameters)
            reject("error_code", "Error message", errorObj)
            return
        }

        if (customFileName != nill) {
            fileName = customFileName
        } else {
            fileName = url.lastPathComponent
        }
        let resDocPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).last!
        let filePath = resDocPath.appendingPathComponent(fileName)

        do {
            try data.write(to: filePath)
            parameters["success"] = true
            parameters["message"] = "File Saved"
            resolve(parameters)
        } catch {
            parameters["success"] = false
            parameters["message"] = "Error creating file: \(error)"
            let errorObj = NSError(domain: "", code: 0, userInfo: parameters)
            reject("error_code", "Error message", errorObj)
        }
    }

    task.resume()
}


  @objc(multiply:withB:withResolver:withRejecter:)
  func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
    resolve(a*b)
  }
}
