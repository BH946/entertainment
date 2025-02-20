from fastapi import APIRouter, Depends, Request, HTTPException  # HTTPException 추가
from slowapi import Limiter
from slowapi.util import get_remote_address
from sqlalchemy.orm import Session
from ..models.database import get_db
from ..schemas.tarot import TarotRequest, TarotResponse
from ..services.perplexity_service import PerplexityService
from ..services.tarot_service import TarotService

router = APIRouter()
limiter = Limiter(key_func=get_remote_address)
perplexity_service = PerplexityService()

@router.post("/reading", response_model=TarotResponse)
@limiter.limit("5/hour")  # IP당 시간당 5회로 제한
async def get_tarot_reading(
    request: Request,
    tarot_request: TarotRequest,
    db: Session = Depends(get_db)
):
    try:
        reading = await perplexity_service.generate_reading(
            tarot_request.situation,
            tarot_request.question
        )
        # 리딩 기록 저장
        tarot_service = TarotService(db)
        await tarot_service.record_reading(
            request.client.host,
            tarot_request.situation,
            tarot_request.question,
            reading
        )
        return TarotResponse(reading=reading)
    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail=str(e)
        )
