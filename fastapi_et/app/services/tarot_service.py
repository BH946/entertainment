from sqlalchemy.orm import Session
from ..models.tarot import ReadingHistory

class TarotService:
    def __init__(self, db: Session):
        self.db = db

    async def record_reading(self, ip_address: str, situation: str, question: str, reading: str):
        reading_record = ReadingHistory(
            ip_address=ip_address,
            situation=situation,
            question=question,
            reading=reading
        )
        self.db.add(reading_record)
        self.db.commit()
