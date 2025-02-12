// app/tarot/category/wealth/page.tsx
'use client';
import { useState } from 'react';
import TarotCard from '@/components/TarotCard';
import ReadingResult from '@/components/ReadingResult'

interface ReadingResultDto {
  message: string;
  // 필요한 다른 필드들도 여기에 추가
}

export default function TarotPage() {
  const [selectedCard, setSelectedCard] = useState<number | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [reading, setReading] = useState<ReadingResultDto | null>(null);

  const handleCardSelect = async (randomCard: number) => {
    //cardNumber(=randomCard) 를 그대로 사용하지 않고 여기서 그냥 램덤 카드 선택하게끔 ㄱㄱㄹ
    setIsLoading(true);
    randomCard = Math.floor(Math.random() * 22); // 0~21 사이의 랜덤 숫자 생성
    setSelectedCard(randomCard);

    try {
      const pathArray = window.location.pathname.split('/');
      const response = await fetch(`http://localhost:8080/api/v1/cardReading/category/${randomCard}/${pathArray[pathArray.length-1]}`);
      const data = await response.json();
      setReading(data);
    } catch (error) {
      console.error('Reading failed:', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen py-8 px-4">
      <div className="max-w-4xl mx-auto">
        {!selectedCard ? (
          <div className="text-center">
            <TarotCard onCardSelect={handleCardSelect} />
          </div>
        ) : (
          <div className="animate-fadeIn">
            {isLoading ? (
              <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-purple-500"></div>
              </div>
            ) : (
              <div className="bg-gray-800 rounded-xl p-8 shadow-xl">
                {reading && (
                  <div className="space-y-6">
                    <div className="text-lg text-white/90">
                      {/* {reading.message} */}
                      <ReadingResult reading={reading}></ReadingResult>
                    </div>
                    <button
                      onClick={() => {
                        setSelectedCard(null);
                        setReading(null);
                      }}
                      className="mt-8 w-full bg-gradient-to-r from-purple-500 to-pink-500 
                               text-white py-3 rounded-lg hover:opacity-90 transition-opacity"
                    >
                      다시 선택하기
                    </button>
                  </div>
                )}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}
