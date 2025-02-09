// components/TarotCard.tsx
interface TarotCardProps {
  onCardSelect: (cardNumber: number) => void
}

// components/TarotCard.tsx
export default function TarotCard({ onCardSelect }: TarotCardProps) {
  return (
    <div className="relative h-[400px] sm:h-[500px] md:h-[600px] w-full max-w-6xl mx-auto p-4 sm:p-6 md:p-8 overflow-x-auto">
      <h1 className="text-2xl sm:text-3xl md:text-4xl font-bold font-serif text-center mb-8 sm:mb-12 md:mb-16 text-yellow-500">
        카드를 선택하세요
      </h1>
      <div className="relative flex justify-start md:justify-center">
        {Array.from({ length: 22 }, (_, i) => (
          <div
            key={i}
            className="absolute cursor-pointer transition-transform duration-300 hover:-translate-y-6"
            style={{
              left: `calc(45% - ${11 * 20}px + ${i * 20}px)`,
              zIndex: i,
            }}
            onClick={() => onCardSelect(i)}
          >
            <div className="w-[100px] sm:w-[120px] md:w-[140px] 
                          h-[160px] sm:h-[190px] md:h-[220px] 
                          bg-gray-900 border-2 border-yellow-500/50 
                          rounded-lg flex items-center justify-center shadow-xl">
              <div className="w-full h-full p-2 sm:p-3 md:p-4">
                <div className="w-full h-full border border-yellow-500/30 rounded 
                              flex items-center justify-center">
                  {i === 21 ? (
                    <div className="text-yellow-500/80">
                      <svg className="w-8 sm:w-12 md:w-16 h-8 sm:h-12 md:h-16" viewBox="0 0 24 24">
                        <path fill="currentColor" 
                              d="M12 2l3 6 7 1-5 4 2 7-7-4-7 4 2-7-5-4 7-1z"/>
                      </svg>
                    </div>
                  ) : (
                    <div className="w-2 sm:w-2.5 md:w-3 h-2 sm:h-2.5 md:h-3 rounded-full bg-yellow-500/50"/>
                  )}
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
