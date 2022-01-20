//
//  ViewController.swift
//  test9
//
//  Created by Mariam  on 1/18/22.
//  Copyright © 2022 Mariamfarghaly.aucegypt. All rights reserved.
//

import UIKit

class WatsonViewController: UIViewController,UIPickerViewDelegate,UIPickerViewDataSource{

    @IBOutlet weak var buttons: UIStackView!
    var pickerData: [String] = [String]()
    @IBOutlet weak var picker: UIPickerView!
    @IBOutlet weak var textView: UILabel!
    @IBOutlet weak var progressView: UIProgressView!
    @IBOutlet weak var reportBtn: UIButton!
    
    @IBOutlet weak var parkhere: UIButton!
    @IBAction func parkButton(_ sender: Any) {
        // create the alert
        let alert = UIAlertController(title: "Message", message: "Parking recorded successfully", preferredStyle: .alert)
        // add an action (button)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        // show the alert
        
        self.present(alert, animated: true, completion: nil)
    }
    
    @IBAction func reportMessage(_ sender: Any) {
        
        // create the alert
        let alert = UIAlertController(title: "Message", message: "Parking report status sent successfully", preferredStyle: .alert)
        // add an action (button)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        
        // show the alert
        self.present(alert, animated: true, completion: nil)
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return pickerData.count
    }
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return pickerData[row]
    }
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if(pickerData[row]==""){
            parkhere.isEnabled = false
            reportBtn.isEnabled=false}
        else{
            parkhere.isEnabled = true
            reportBtn.isEnabled=true}
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        progressView.transform = progressView.transform.scaledBy(x: 1, y: 7)
        reportBtn.isEnabled=false
        parkhere.isEnabled = false
        pickerData = ["","Empty","Almost empty","Halfway filled","Almost filled","Completely full"]
        picker.dataSource = self
        picker.delegate = self
        //current count/total count
        progressView.setProgress(0.5,animated:false)
        //set status according to database
        textView.text = "The parking is halfway filled!"
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
