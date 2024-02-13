import classNames from 'classnames';
import { useState } from 'react';

export default function TimeSlots({ timeSlots, handleTime, handleUnselect }) {
  const [activeSlot, setActiveSlot] = useState(null);

  const handleClick = (time) => {
    if (activeSlot === time) {
      setActiveSlot(null);
      handleUnselect();
    } else {
      setActiveSlot(time);
      handleTime(time);
    }
  };

  return (
    <div className="available-times">
      {timeSlots.map(slot => (
        <div key={slot.time} className={classNames('time-slot', { 'active': activeSlot === slot.time, 'available': slot.available })}
          onClick={slot.available ? () => handleClick(slot.time) : null}>
          <span className="time_text">{slot.time} : 00</span>
        </div>
      ))}
    </div>
  );
};
