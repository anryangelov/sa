'''
very basic client for interacting with sensor REST API
requests lib is needed to be install
'''

import requests
import argparse
from pprint import pprint


uri_get = 'http://127.0.0.1:8080/sa/rest/sensors/'
uri_post = 'http://127.0.0.1:8080/sa/rest/sensors/add/'


def req(res):
    if res.ok:
        return res.json()
    return res.status_code, res.reason


def GET(type_sen, id_sen):
    uri = uri_get + type_sen
    if id_sen is not None:
        uri = uri + '/' + str(id_sen)
    print("\nurl: {}\n".format(uri))
    response = requests.get(uri)
    return req(response)


def POST(type_sen, id_sen):
    if id_sen is not None:
        print("id argument is not needed when adding sensor")
        exit()
    uri = uri_post + type_sen
    print("\nurl: {}\n".format(uri))
    response = requests.post(uri)
    return req(response)


parser = argparse.ArgumentParser()
parser.add_argument("type", help="type of sensor")
parser.add_argument("-id", help="id's Sensor", type=int)
parser.add_argument("--add-sensor", help="add sensor send POST requset",
                    action="store_true")

args = parser.parse_args()

type_sensor = args.type
ID = args.id
method = 'GET'
if args.add_sensor:
    method = 'POST'


if method == 'GET':
    pprint(GET(type_sensor, ID))
elif method == 'POST':
    pprint(POST(type_sensor, ID))
