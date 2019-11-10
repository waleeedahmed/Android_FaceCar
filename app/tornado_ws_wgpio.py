import tornado.ioloop
import tornado.web
import tornado.websocket
import RPi.GPIO as GPIO
from time import sleep
from tornado.options import define, options, parse_command_line

define("port", default=8888, type=int)

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)


msg = ""
i = 0
buzzerLow = 21
buzzerHigh = 26
red = 12
yellow = 16
green = 20


GPIO.setup(buzzerLow,GPIO.OUT)
GPIO.setup(buzzerHigh,GPIO.OUT)
GPIO.setup(red,GPIO.OUT)
GPIO.setup(yellow,GPIO.OUT)
GPIO.setup(green,GPIO.OUT)





class IndexHandler(tornado.web.RequestHandler):
    def get(self):
        self.render("index.html")


class WebSocketHandler(tornado.websocket.WebSocketHandler):
    def open(self, *args):
        print ("New connection")
        self.write_message("Welcome!")
        GPIO.output(yellow,GPIO.HIGH)

    def on_message(self, message):
        print ("New message {}").format(message)
        self.write_message(message.upper())
        global msg
        msg = message
        
        if msg == "1":
            i=0
            while i < 3:
                GPIO.output(green,GPIO.HIGH)
                GPIO.output(buzzerLow,GPIO.HIGH)
                sleep(0.1)
                GPIO.output(green,GPIO.LOW)
                GPIO.output(buzzerLow,GPIO.LOW)
                sleep(0.1)
                i = i + 1;
        elif msg == "0":
            i=0
            while i < 3:
                GPIO.output(red,GPIO.HIGH)
                GPIO.output(buzzerHigh,GPIO.HIGH)
                sleep(0.1)
                GPIO.output(red,GPIO.LOW)
                GPIO.output(buzzerHigh,GPIO.LOW)
                sleep(0.1)
                i = i + 1;
            

    def on_close(self):
        print ("Connection closed")
        GPIO.output(yellow,GPIO.LOW)


app = tornado.web.Application([
    (r'/', IndexHandler),
    (r'/ws/', WebSocketHandler),
])


if __name__ == '__main__':
    app.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()
    

