import classNames from "classnames";

const SearchSelect = ({ label, selectRef, options }) => {
  return (
    <div className={classNames("search-item")}>
      <span>{label}</span>
      <select ref={selectRef}>{options}</select>
    </div>
  );
};

export default SearchSelect;
