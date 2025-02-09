// app/page.tsx
export default function HomePage() {
  return (
    <div className="min-h-[80vh] flex flex-col items-center justify-center py-12 bg-gray-800">
      <div className="text-center max-w-2xl mx-auto px-4">
        <h1 className="text-5xl font-bold mb-6 bg-gradient-to-r from-purple-300 to-pink-300 text-transparent bg-clip-text">
          타로 리딩
        </h1>
        <p className="text-xl text-purple-200 mb-8 leading-relaxed">
          당신의 운명을 타로 카드에 담아보세요. <br/>
          오늘의 운세와 인사이트를 발견하실 수 있습니다.
        </p>
        <a 
          href="/tarot" 
          className="inline-block px-8 py-3 rounded-full bg-gradient-to-r from-purple-500 to-pink-500 text-white font-semibold 
          hover:from-purple-600 hover:to-pink-600 transform hover:scale-105 transition-all duration-200 shadow-lg"
        >
          타로 카드 선택하기
        </a>
      </div>
    </div>
  )
}
