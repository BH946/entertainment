// components/ReadingResult.tsx
interface Reading {
  nameKr: string
  nameEn: string
  cardNumber: number
  imageUrl: string
  keyword: string
  description: string
}

export default function ReadingResult({ reading }: { reading: Reading }) {
  return (
    <div className="max-w-4xl mx-auto p-6">
      <div className="bg-white/10 backdrop-blur-md rounded-2xl p-8 shadow-2xl">
        <div className="text-center mb-10">
          <h2 className="text-3xl font-bold mb-3 bg-gradient-to-r from-purple-300 to-pink-300 text-transparent bg-clip-text">
            {reading.nameKr}
          </h2>
          <p className="text-purple-200 text-lg">{reading.nameEn}</p>
        </div>
        
        <div className="mb-10">
          <img
            src={reading.imageUrl}
            alt={reading.nameKr}
            className="mx-auto max-w-full h-auto rounded-xl shadow-lg transform hover:scale-105 transition-all duration-300"
          />
        </div>

        <div className="bg-white/5 p-8 rounded-xl backdrop-blur-sm">
          <p className="text-xl font-semibold mb-6 text-purple-200">{reading.keyword}</p>
          <p className="text-gray-200 leading-relaxed text-lg">{reading.description}</p>
        </div>
      </div>
    </div>
  )
}
