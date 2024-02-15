import React, { useRef } from 'react';
import { motion } from 'framer-motion';
import { useState, useEffect } from 'react';

const AnimatedComponent = ({
  children,
  name = 'animation',
  delay = 0.3,
  duration = 0.2,
  yDuration = 0.8,
  scale = 1,
  translateY = 50,
  triggerPosition = 0.8,
}) => {
  const [isVisible, setIsVisible] = useState(false);
  const componentPosition = useRef();

  useEffect(() => {
    const page = document.querySelector('#root').querySelector('div');
    const position = document.querySelector(`.${name}`).getBoundingClientRect().top;
    componentPosition.current = position;

    const handleScroll = () => {
      const scrollPosition = page.scrollTop + window.innerHeight * triggerPosition;

      if (scrollPosition > componentPosition.current && !isVisible) {
        setIsVisible(true);
      }
    };

    page.addEventListener('scroll', handleScroll);
  });

  const containerStyle = {
    opacity: 0,
    y: translateY,
  };

  const animationStyle = {
    opacity: 1,
    y: 0,
    transition: { delay },
  };

  return (
    <motion.div
      className={name}
      initial={containerStyle}
      animate={isVisible ? animationStyle : {}}
      whileHover={{ scale }}
      transition={{
        ease: 'easeInOut',
        duration: { duration },
        y: { duration: { yDuration } },
      }}
      style={{ width: '100%' }}
    >
      {children}
    </motion.div>
  );
};

export default AnimatedComponent;
