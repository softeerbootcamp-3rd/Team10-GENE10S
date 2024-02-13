import React from 'react';
import { motion } from 'framer-motion';
import { useState, useEffect } from "react";

const AnimatedComponent = ({ children, name = "animation",
  delay = 0.3, duration = 0.2, yDuration = 0.8, scale = 1, translateY = 50, triggerPosition = 0.5 }) => {
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      const scrollPosition = window.scrollY + window.innerHeight * triggerPosition;
      const componentPosition = document
        .querySelector("." + name)
        .getBoundingClientRect().top + window.scrollY;

      if (scrollPosition > componentPosition && !isVisible) {
        setIsVisible(true);
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, [isVisible, triggerPosition]);

  const containerStyle = {
    opacity: 0,
    y: translateY
  };

  const animationStyle = {
    opacity: 1,
    y: 0,
    transition: { delay }
  };

  return (
    <motion.div
      className={ name }
      initial={containerStyle}
      animate={ isVisible ? animationStyle : {} }
      whileHover={{ scale }} 
      transition={{
        ease: "easeInOut",
        duration : { duration },
        y: { duration : { yDuration } }
      }}
      style={{ width: "100%" }}
    >
      {children}
    </motion.div>
  );
}

export default AnimatedComponent;