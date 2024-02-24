import classNames from "classnames";

const SearchInput = ({ label, inputRef }) => {
  return (
    <div className={classNames("search-item")}>
      <span>{label}</span>
      <input type='text' ref={inputRef} />
    </div>
  );
};

export default SearchInput;
