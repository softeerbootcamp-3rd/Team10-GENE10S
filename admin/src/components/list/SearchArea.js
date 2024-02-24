import classNames from 'classnames'

const SearchArea = ({ children }) => {
  return (
    <div className={classNames('search-area')}>
      {children}
    </div>
  )
}

export default SearchArea
