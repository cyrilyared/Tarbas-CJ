
import os
import sys
import time
import random


import tensorflow as tf
import numpy as np
import time
from PIL import Image, ImageDraw
from object_detection.utils import label_map_util, visualization_utils
import glob
from six.moves import urllib
import tarfile

##### Constants
DOWNLOAD_BASE = 'http://download.tensorflow.org/models/object_detection/'
MODEL_NAME = 'faster_rcnn_resnet50_coco_2018_01_28'
PATH_TO_OBJECT_DETECTION_REPO_UTIL = os.environ["PYTHONPATH"]  # Insert path to tensorflow object detection repository - models/research/object_detection/ 
PATH_TO_OBJECT_DETECTION_REPO = os.environ["OBJECTPATH"]
PATH_TO_LABELS = PATH_TO_OBJECT_DETECTION_REPO + "data/mscoco_label_map.pbtxt"
NUM_CLASSES = 1
BOUND_BOXES_OUTPUT_FILE = "boxes.csv"

##### config variables to set 
threshold = 0.35
test_image_dir = "./images"    # Insert path to directory containing test images

def download_model(model_name):
    """Download the model from tensorflow model zoo
    Args:
        model_name: name of model to download
    """
    model_file = model_name + '.tar.gz'
    if os.path.isfile(model_name + '/frozen_inference_graph.pb'):
        print("File already downloaded")
        return
    opener = urllib.request.URLopener()
    try:
        print("Downloading Model")
        opener.retrieve(DOWNLOAD_BASE + model_file, model_file)
        print("Extracting Model")
        tar_file = tarfile.open(model_file)
        for file in tar_file.getmembers():
            file_name = os.path.basename(file.name)
            if 'frozen_inference_graph.pb' in file_name:
                tar_file.extract(file, os.getcwd())
        print("Done")
    except:
        raise Exception("Not able to download model, please check the model name")


def draw_rectangle(draw, coordinates, color, width=1):
    for i in range(width):
        rect_start = (coordinates[1] - i, coordinates[0] - i)
        rect_end = (coordinates[3] + i, coordinates[2] + i)
        draw.rectangle((rect_start, rect_end), outline = color)

def printCoordToFile(boxes, image_name, im_width, im_height):
    arr = np.empty((0,2), int)
    for b in boxes:
        x_coord = (b[1]+b[3])*100/(im_width*2)
        y_coord = (b[0]+b[2])*100/(im_height*2)
        arr = np.append(arr, np.array([[x_coord, y_coord]]), axis=0)
    np.savetxt(image_name + "_" + BOUND_BOXES_OUTPUT_FILE, arr, delimiter = ',')

class ObjectDetectionPredict():
    """class method to Load tf graph and 
    make prediction on test images using predict function
    """
    
    def __init__(self, model_name):
        """ Downloads, initialize the tf model graph and stores in Memory
        for prediction
        """
        download_model(model_name)
        label_map = label_map_util.load_labelmap(PATH_TO_LABELS)
        categories = label_map_util.convert_label_map_to_categories(label_map, 
                                                                    max_num_classes=NUM_CLASSES, use_display_name=True)
        self.category_index = label_map_util.create_category_index(categories)
        self.load_graph(model_name)
    
    def load_graph(self, model_name):
        """ Loads the model into RAM
        Args:
        model_name: name of model to load
        """
        model_file = model_name + '/frozen_inference_graph.pb'
        self.detection_graph = tf.Graph()
        graph_def = tf.GraphDef()
        with open(model_file, "rb") as f:
            graph_def.ParseFromString(f.read())
        with self.detection_graph.as_default():
            tf.import_graph_def(graph_def)

        self.sess = tf.Session(graph=self.detection_graph)
        
        self.image_tensor = self.detection_graph.get_operation_by_name('import/image_tensor')
        self.boxes = self.detection_graph.get_operation_by_name('import/detection_boxes')
        self.scores = self.detection_graph.get_operation_by_name('import/detection_scores')
        self.classes = self.detection_graph.get_operation_by_name('import/detection_classes')
        self.num_detections = self.detection_graph.get_operation_by_name('import/num_detections')
        return 0
    

    def predict_single_image(self, image_file_path, image_name):
            """make object detection prediction on single image
            Args:
            image_file_path: Full path of image file to predict
            """
            image = Image.open(image_file_path)
            im_width, im_height = image.size
            image_np = np.array(image.getdata())[:,0:3].reshape((im_height, im_width, 3)).astype(np.uint8)
            image_np_expanded = np.expand_dims(image_np, axis=0)

            (boxes, scores, classes, num_detections) = self.sess.run(
                [self.boxes.outputs[0], self.scores.outputs[0], self.classes.outputs[0], self.num_detections.outputs[0]],
                feed_dict={self.image_tensor.outputs[0]: image_np_expanded})

            # Discard detections that do not meet the threshold score
            correct_prediction = [(s, np.multiply(b, [im_height, im_width, im_height, im_width]), c) 
                                                    for c, s, b in zip(classes[0], scores[0], boxes[0]) if (s > threshold and c in self.category_index)]
                    
            if correct_prediction:
                scores, boxes, classes = zip(*correct_prediction)

                box_selector = []

                for s, b, c in correct_prediction:
                    x1, x2, y1, y2 = b[1], b[3], b[0], b[2]
                    x_width = max(x2-x1, x1-x2)
                    y_height = max(y2-y1, y1-y2)
                    if x_width < 0.20 * im_width:
                        if y_height < 0.45 * im_height:
                            if(x_width < 0.7 * y_height):
                                box_selector.append(b)

                draw = ImageDraw.Draw(image)
                for boxes in box_selector:
                    draw_rectangle(draw, boxes, 'red', 1)
                printCoordToFile(box_selector, image_name, im_width, im_height)
                
            return scores, classes, image, boxes

if __name__ == "__main__":
    prediction_class = ObjectDetectionPredict(model_name=MODEL_NAME)
    all_test_images = glob.glob("%s/*.jpg"%(test_image_dir.rstrip('/')))
    print("Number of Test Images : ", len(all_test_images))

    for image in all_test_images:
        #### boxes are in [ymin. xmin. ymax, xmax] format
        scores, classes, img, boxes = prediction_class.predict_single_image(image, image.rsplit('.', 1)[0])
        image_name, ext = image.rsplit('.', 1)
        new_image_name = image_name + "_prediction." + ext
        img.save(new_image_name)
    prediction_class.sess.close()
