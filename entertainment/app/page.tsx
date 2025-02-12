// app/page.tsx
export default function HomePage() {
  const sections = [
    {
      title: "타로 리딩",
      description: "당신의 운명을 타로 카드에 담아보세요.",
      href: "/tarot",
    },
    // 향후 추가될 다른 섹션들을 위한 공간
    {
      title: "테스트",
      description: "테스트용 설명",
      href: "/",
    },
    {
      title: "테스트",
      description: "테스트용 설명",
      href: "/",
    },
    {
      title: "테스트",
      description: "테스트용 설명",
      href: "/",
    },
    {
      title: "테스트",
      description: "테스트용 설명",
      href: "/",
    },{
      title: "테스트",
      description: "테스트용 설명",
      href: "/",
    },{
      title: "테스트",
      description: "테스트용 설명",
      href: "/",
    },
  ];

  return (
    <div>
    <h1 className="text-6xl font-bold mb-16 bg-gradient-to-r from-purple-300 to-yellow-500 text-transparent bg-clip-text text-center">
          Entertainment Hub
    </h1>
    <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8 ">
          {sections.map((section, index) => (
            <a
              key={index}
              href={section.href}
              className={`bg-gray-800  
                         p-12 rounded-xl shadow-xl hover:scale-105 
                         transition-transform duration-300`}
            >
              <h2 className="text-4xl font-bold mb-6 bg-gradient-to-r from-purple-300 to-pink-300 text-transparent bg-clip-text">
                {section.title}
              </h2>
              <p className="text-xl text-purple-200  leading-relaxed">
                {section.description}
              </p>
            </a>
          ))}
        </div>
    </div>
  );
}
