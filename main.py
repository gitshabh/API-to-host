from fastapi import FastAPI
from model import User
import base64
import face_recog

app = FastAPI()

encoded_image:str

@app.post("/api/v1/users")
async def get_picture(user: User):
    encoded_image = user.base64_string

    decodeit = open('user.jpeg', 'wb')
    decodeit.write(base64.b64decode((encoded_image)))
    decodeit.close()

    return {"yes":"bitches"}

@app.get("/api/v1/users")
async def recognize_user():
    name = face_recog.face_rec()

    return {"name" : name}






