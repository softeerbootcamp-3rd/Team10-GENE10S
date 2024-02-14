import classNames from 'classnames';

export default function TimeSlots({ timeSlots, selectedTime, handleClickTime }) {

  return (
    <div className="available-times">
      {timeSlots.map(slot => (
        <div key={slot.time} className={classNames('time-slot', { 'active': selectedTime === slot.time, 'available': slot.available })}
          onClick={slot.available ? () => handleClickTime(slot.time) : null}>
          <span className="time_text">{slot.time} : 00</span>
        </div>
      ))}
    </div>
  );
};
