from pydantic import BaseModel
from typing import Optional

class TarotRequest(BaseModel):
    situation: str
    question: Optional[str] = None

class TarotResponse(BaseModel):
    reading: str
