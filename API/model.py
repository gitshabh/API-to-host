from pydantic import BaseModel

class User(BaseModel):
    base64_string: str


    