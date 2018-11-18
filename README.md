# Tarbas McGill CodeJam Repository

Our society is governed by lines (or queues as they call it on the other side of the pond). We see lines everywhere from checkout lines to security lines at airports to lines to visit tourist attractions to lines of cars waiting during a traffic jam. When it comes to lines, the most important question always seems to be, "when will I get out of this line." The goal of Tarbas is to find this answer.

Our software system takes an image of any format, determines where the individuals are in this image, and then determines whether these individuals are in a line.

## Person Detection
The main file which is used to draw bounding boxes and determine the coordinates of individuals in an image is `person_detection.py`. It is based on the Google TensorFlow Object Detection API and the file is based on the work of Norman Di Palo as found [here](https://medium.com/nanonets/how-i-built-a-self-flying-drone-to-track-people-in-under-50-lines-of-code-7485de7f828e). All images in the directory specified by `IMAGES_DIRECTORY` in `tarbas.java` will be processed. It will output processed images with bounding boxes identifying individuals as shown below. 

The central point of each bounding box is also saved as a `(x, y)` pair in a CSV file for that image so that this data can later be used to identify lines.

Without Bounding Boxes     |  With Bounding Boxes
:-------------------------:|:-------------------------:
![](https://lh3.googleusercontent.com/22S6YkD_g0TCVTBJRigKrEEaA4TaDx3s8ZS3bIPz8CkyDJf80-ujOSfyW7H0ZOlG6BffDtwYIc-lTHngrGPynB_58CzTMQKMDZA2QoKeu4xqsnD9qsRRFm3h_0-LqPly_vndkc3nAWaXXD2z3kItkZi1-ewHWDAJS5o7CTCqpWiU1CLu3bZ_bNXZf0TW0gxaF_pudNMJrjGh1HYyUvTp1Y4rYLqhsyrHeqT7pbvhuPIWYtx4MHrDMD9POimdnn4c0NmhIa-AdjXD1WCJwoAk0SovTPUEhzHJQDuMV2qjI1tTLf_3iqXHyEJCwuk6mPYnmulUY2quRbSjT8RICAuW-hX0v4eTcYEvWcRDV7rfExwPCbnzQqqW12NRK9KBUr3BCTEm79xkHAwB-bJSpyQCrWuvtc3G8TWX5v77rTWbHoUVjcofgERrNUaKfDn-hodAb-ZfMr2iLiHNftuFOWlQL7oPsa-9sxQXZawKqSRnwG2FP4ASiXin3_Ub4BAruMtVe28OMuiW7cYkPOH6wljpWH-hBnj_qQGTQAa4ChnIA8i3IGQEzHnXn_bi8UYhjUUx9S6RUwE-gQztrzsCbs7oHpyWn9lDfk4H=w1440-h757) | ![](https://lh3.googleusercontent.com/R7_nVAxsdbLEGpZ8tLGp0BfogS0X0JUAo2kqxDRBAQGuZcV9EMcb4vufOcaBKqLzr6zWgoSkH2wCZJFPS-OUfW9yuEtDNbTfyHuJ6RgUpVim5W1Bl88UGOBdnHw4t5L1zMMjP0rldL3JnuVOjikHwzL_H82SvxZEgsFlC0G1OEbOe0tXkKGoXitCOPhOqpWAn-sAsKx5WHDtjF_j1mSGaMtHKkj569mUzn9msGPaM8Uj_8V8glFVfc1knolnXaBxbvoqYsP4lWibcEZe7hjX2qYQlRxetpN2Oro6bnRCGNJQIuN3NCVES2IaxhoIL1lVy9_z7XjeMsHxOhLlV1ZDiZEl89i-FUxLJb87-TliT8vf7LwSpVmYyBMvtTaHuMnAjt1buJxtbgndr6Cc-4dhestnojh7K_Zb63k0Zq0Rj8OWLwLfy51L5w8lvtYfvChy30Dg4lq89NkVIG-YOKleehLdGp5_xXqqIAZApLSHGT10wYGwmgnBPJmJEw31NxEpOBdygfnGCY_T5Zse1__9_ALy55ScfcRpnDcB7Pwz2zDaAXxHwK31e64PwVLGm2AlzQYsCCP9aFOcXyfg2kIfo6286Gvif1gZ=w1440-h757)

## Linear Regression

In order to determine whether the sets of `(x, y)` points approximate a line, we use a basic linear regression algorithm provided by scikit-learn in `linearReg.py`. This returns a R^2 score which we use to determine how closely these `(x, y)` points meet a model of a line.


Contributors: Alexander Asfar, Michel Majdalani, Cyril Yared
