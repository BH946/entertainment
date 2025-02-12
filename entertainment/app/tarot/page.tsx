// app/tarot/page.tsx
'use client';
import { useState } from 'react';

interface Category {
  title: string;
  description: string;
  href?: string;
  gradient: string;
}

export default function TarotMainPage() {
  const [showCategories, setShowCategories] = useState(false);

  const categories = [
    {
      title: "오늘의 타로",
      description: "오늘 당신을 위한 특별한 메시지",
      href: "/tarot/daily",
      gradient: "from-purple-500 to-pink-500"
    },
    {
      title: "카테고리별 타로",
      description: "삶의 각 영역에 대한 깊이 있는 통찰",
      gradient: "from-blue-500 to-indigo-500"
    },
    {
      title: "네/아니오 타로",
      description: "명확한 답변이 필요한 질문을 위한 타로",
      href: "/tarot/yesno",
      gradient: "from-green-500 to-teal-500"
    }
  ];

  const subCategories = ["HEALTH", "CAREER", "WEALTH", "LOVE"];

  const handleCategoryClick = (e: React.MouseEvent, category: Category) => {
    if (category.title === "카테고리별 타로") {
      e.preventDefault();
      setShowCategories(!showCategories);
    }
  };

  return (
    <div className="min-h-screen bg-gray-800 py-12 px-4">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-5xl font-bold mb-16 bg-gradient-to-r from-purple-300 to-pink-300 text-transparent bg-clip-text text-center">
          타로 리딩
        </h1>
        <div className="grid md:grid-cols-3 gap-8">
          {categories.map((category, index) => (
            <div key={index} className="relative">
              <a
                href={category.href}
                onClick={(e) => handleCategoryClick(e, category)}
                className={`bg-gradient-to-r ${category.gradient} 
                           p-6 rounded-xl shadow-xl hover:scale-105 
                           transition-transform duration-300 block cursor-pointer`}
              >
                <h2 className="text-2xl font-bold text-white mb-3">
                  {category.title}
                </h2>
                <p className="text-white/80">
                  {category.description}
                </p>
              </a>
              {category.title === "카테고리별 타로" && showCategories && (
                <div className="absolute top-full left-0 right-0 mt-4 z-10 bg-gray-800 rounded-xl overflow-hidden">
                  <div className="grid gap-2 p-4">
                    {subCategories.map((subCat, idx) => (
                      <a
                        key={idx}
                        href={`/tarot/category/${subCat.toLowerCase()}`}
                        className="bg-white/10 px-6 py-4 rounded-lg text-center
                                 hover:bg-white/20 transition-colors duration-300 cursor-pointer"
                      >
                        <span className="text-white font-medium">{subCat}</span>
                      </a>
                    ))}
                  </div>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
