import classNames from 'classnames'

const SearchRow = ({ children }) => {
  return (
    <div className={classNames('search-row')}>
      {children}
    </div>
  )
}

export default SearchRow
