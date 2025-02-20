from sqlalchemy import Column, Integer, String, DateTime, Text
from sqlalchemy.sql import func
from .database import Base

class ReadingHistory(Base):
    __tablename__ = "reading_history"

    id = Column(Integer, primary_key=True, index=True)
    ip_address = Column(String(50))
    situation = Column(Text)
    question = Column(Text, nullable=True)
    reading = Column(Text)
    created_at = Column(DateTime(timezone=True), server_default=func.now())
