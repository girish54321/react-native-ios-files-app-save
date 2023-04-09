@objc(IosFilesAppSave)
class IosFilesAppSave: NSObject {
    
    @objc
    func startDownload(_ options: NSDictionary, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
        var parameters: [String: Any] = ["message" : ""]
        var urlString: String? = ""
        urlString = options["url"] as? String
        let customFileName: String? = options["fileName"] as? String
        let isBase64: Bool? = options["isBase64"] as? Bool
        
        if ((isBase64 != nil) == true) {
            if (customFileName == nil) {
                parameters["success"] = false
                parameters["message"] = "File name is reqiored for BASE64"
                resolve(parameters)
                return
            }
            guard var documentsURL = (FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)).last,
                  let convertedData = Data(base64Encoded: urlString ?? "")
            else {
                parameters["success"] = false
                parameters["message"] = "Error with base64"
                resolve(parameters)
                return
            }
            
            let resDocPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).last!
            let filePath = resDocPath.appendingPathComponent(customFileName ?? "")
            
            do {
                try convertedData.write(to: filePath)
                parameters["success"] = true
                parameters["message"] = "File Saved"
                parameters["path"] = getFilePath(url: resDocPath)
                resolve(parameters)
            } catch {
                parameters["success"] = false
                parameters["message"] = "Error creating file: \(error)"
                resolve(parameters)
            }
            return
        }
        
        guard let url = URL(string: urlString ?? "") else {
            parameters["success"] = false
            parameters["message"] = "Invalid URL"
            resolve(parameters)
            return
        }
        
        let task = URLSession.shared.dataTask(with: url) { [self] (data, response, error) in
            if let error = error {
                parameters["success"] = false
                parameters["message"] = "Error downloading file: \(error)"
                resolve(parameters)
                return
            }
            
            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                parameters["success"] = false
                parameters["message"] = "Invalid status code"
                resolve(parameters)
                return
            }
            
            guard let data = data else {
                parameters["success"] = false
                parameters["message"] = "No data downloaded"
                resolve(parameters)
                return
            }
            var fileName = ""
            if (customFileName != nil) {
                fileName = customFileName!
            } else {
                fileName = url.lastPathComponent
            }
            let resDocPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).last!
            let filePath = resDocPath.appendingPathComponent(fileName)
            
            do {
                try data.write(to: filePath)
                parameters["success"] = true
                parameters["message"] = "File Saved"
                parameters["path"] = getFilePath(url: resDocPath)
                resolve(parameters)
            } catch {
                parameters["success"] = false
                parameters["message"] = "Error creating file: \(error)"
                resolve(parameters)
            }
        }
        
        task.resume()
    }
    
    func getFilePath(url:URL) -> String? {
        var filePath: String? = nil
        do {
            let directoryContents = try FileManager.default.contentsOfDirectory(at: url, includingPropertiesForKeys: nil)
            filePath = String(describing: directoryContents[0])
        } catch {
            print(error)
        }
        return filePath
    }
    
    @objc(multiply:withB:withResolver:withRejecter:)
    func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        resolve(a*b)
    }
}
