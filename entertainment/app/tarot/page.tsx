// app/tarot/page.tsx
'use client';
import TarotCard from '@/components/TarotCard'
import { useRouter } from 'next/navigation'

export default function TarotPage() {
  const router = useRouter()

  const handleCardSelect = (cardNumber: number) => {
    //cardNumber 를 그대로 사용하지 않고 여기서 그냥 램덤 카드 선택하게끔 ㄱㄱㄹ
    cardNumber = Math.floor(Math.random() * 22); // 0~21 사이의 랜덤 숫자 생성
    router.push(`/tarot/reading?card=${cardNumber}`)
  }

  return (
    <div className="py-8">
      <TarotCard onCardSelect={handleCardSelect} />
    </div>
  )
}
