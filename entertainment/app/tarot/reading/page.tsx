// app/tarot/reading/page.tsx
import ReadingResult from '@/components/ReadingResult'

async function getReading(cardNumber: string) {
  const res = await fetch(`http://localhost:8080/api/v1/cardReading/today/${cardNumber}`)
  if (!res.ok) throw new Error('Failed to fetch reading')
  return res.json()
}

export default async function ReadingPage({
  searchParams,
}: {
  searchParams: { card: string }
}) {
  const reading = await getReading(searchParams.card)
  
  return <ReadingResult reading={reading} />
}
