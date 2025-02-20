from fastapi import FastAPI
from slowapi import Limiter, _rate_limit_exceeded_handler
from slowapi.util import get_remote_address
from slowapi.errors import RateLimitExceeded
from app.routers import tarot
from app.models.database import Base, engine

app = FastAPI(title="Tarot AI Reading API")

# Rate limiter 설정
limiter = Limiter(key_func=get_remote_address)
app.state.limiter = limiter
app.add_exception_handler(RateLimitExceeded, _rate_limit_exceeded_handler)

# 애플리케이션 시작 시 테이블 생성
Base.metadata.create_all(bind=engine)

# 라우터 등록
app.include_router(tarot.router, prefix="/api/v1")
