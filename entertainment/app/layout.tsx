// app/layout.tsx
import './globals.css'  // 이 줄을 추가
import Navigation from '@/components/Navigation'

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="ko">
      <body className="bg-gradient-to-br from-indigo-950 via-purple-900 to-pink-900 min-h-screen text-white">
      <Navigation />
        <main className="container mx-auto px-4">
          {children}
        </main>
      </body>
    </html>
  )
}
