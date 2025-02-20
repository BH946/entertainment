import os
import httpx
from fastapi import HTTPException
from dotenv import load_dotenv

load_dotenv()

class PerplexityService:
    def __init__(self):
        self.api_key = os.getenv("PERPLEXITY_API_KEY")
        if not self.api_key:
            raise ValueError("PERPLEXITY_API_KEY not found in environment variables")
        self.base_url = "https://api.perplexity.ai"
    
    async def generate_reading(self, situation: str, question: str = None) -> str:
        try:
            async with httpx.AsyncClient(timeout=30.0) as client:  # 타임아웃 설정
                response = await client.post(
                    f"{self.base_url}/chat/completions",
                    headers={
                        "Authorization": f"Bearer {self.api_key}",
                        "Content-Type": "application/json"
                    },
                    json={
                        "model": "sonar",
                        "messages": [
                            {
                                "role": "system",
                                "content": "You are an experienced tarot reader providing insightful and accurate readings."
                            },
                            {
                                "role": "user",
                                "content": self._create_prompt(situation, question)
                            }
                        ],
                        "max_tokens": 500, # 응답길이 제한
                        "temperature": 0.7 # 창의성
                    }
                )
                
                if response.status_code != 200:
                    error_detail = response.json().get('error', {}).get('message', 'Unknown error')
                    raise HTTPException(
                        status_code=response.status_code,
                        detail=f"Perplexity API error: {error_detail}"
                    )
                
                return response.json()["choices"][0]["message"]["content"]
                
        except httpx.TimeoutException:
            raise HTTPException(status_code=504, detail="Request timeout")
        except httpx.RequestError as e:
            raise HTTPException(status_code=500, detail=f"Request failed: {str(e)}")
        except Exception as e:
            raise HTTPException(status_code=500, detail=f"Unexpected error: {str(e)}")

    def _create_prompt(self, situation: str, question: str = None) -> str:
        if not situation:
            raise ValueError("Situation cannot be empty")
            
        base_prompt = f"Given the following situation: {situation}"
        if question:
            base_prompt += f"\nWith the specific question: {question}"
        return base_prompt + "\nProvide a concise but meaningful tarot reading with clear insights and guidance."
