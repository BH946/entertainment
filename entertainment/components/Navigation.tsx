// components/Navigation.tsx
'use client';
import Link from 'next/link'
import { usePathname } from 'next/navigation'

export default function Navigation() {
  const pathname = usePathname()
  
  return (
    <nav className="backdrop-blur-md bg-white/5 mb-8">
      <div className="container mx-auto px-6">
        <div className="flex items-center h-16">
          <Link 
            href="/"
            className={`mr-6 font-medium hover:text-purple-300 transition-colors ${
              pathname === '/' ? 'text-purple-300' : 'text-white'
            }`}
          >
            홈
          </Link>
          <Link 
            href="/tarot"
            className={`font-medium hover:text-purple-300 transition-colors ${
              pathname.startsWith('/tarot') ? 'text-purple-300' : 'text-white'
            }`}
          >
            타로
          </Link>
        </div>
      </div>
    </nav>
  )
}
