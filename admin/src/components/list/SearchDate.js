import classNames from "classnames";
import CustomCalendar from "../common/CustomCalendar";

const SearchDate = ({
  label,
  startValue,
  onStartChange,
  endValue,
  onEndChange,
}) => {
  return (
    <div className={classNames("search-item")}>
      <span>{label}</span>
      <CustomCalendar value={startValue} onChange={onStartChange} />
      <div>~</div>
      <CustomCalendar value={endValue} onChange={onEndChange} />
    </div>
  );
};

export default SearchDate;
