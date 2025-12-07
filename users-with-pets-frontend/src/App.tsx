import { useState } from 'react'
//import './App.css'
import { fetchUsers, type UserWithPet } from './api'
import UserCard from './components/UserCard'
import './styles.css'
import { ImDownload3, ImUsers } from 'react-icons/im'

function App() {
  const API_BASE = (import.meta.env.VITE_API_BASE as string) || "http://localhost:8080";
  const [count, setCount] = useState<number>(12)
  const [nat, setNat] = useState<string>('')
  const [users, setUsers] = useState<UserWithPet[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [page, setPage] = useState(1)
  const itemsPerPage = 6
  const countryOptions = [
  { value: "", country: "All Countries" },
  { value: "AU", country: "Australia" },
  { value: "BR", country: "Brazil" },
  { value: "CA", country: "Canada" },
  { value: "CH", country: "Switzerland" },
  { value: "DE", country: "Germany" },
  { value: "DK", country: "Denmark" },
  { value: "ES", country: "Spain" },
  { value: "FI", country: "Finland" },
  { value: "FR", country: "France" },
  { value: "GB", country: "United Kingdom" },
  { value: "IE", country: "Ireland" },
  { value: "IN", country: "India" },
  { value: "IR", country: "Iran" },
  { value: "MX", country: "Mexico" },
  { value: "NL", country: "Netherlands" },
  { value: "NO", country: "Norway" },
  { value: "NZ", country: "New Zealand" },
  { value: "RS", country: "Serbia" },
  { value: "TR", country: "Turkey" },
  { value: "UA", country: "Ukraine" },
  { value: "US", country: "United States" }
]; // Currently, randomuser offers these nationalities

  async function handleFetch() {
    setError(null)
    setLoading(true)
    try {
      setUsers([])
      let data = await fetchUsers(count, nat)
      setUsers(data)
      setPage(1)
    } catch (err: any) {
      setError(err.message || 'Failed to fetch')
      setUsers([])
    } finally {
      setLoading(false)
    }
  }

  let totalPages = Math.ceil(users.length / itemsPerPage)
  let start = (page - 1) * itemsPerPage
  let pageItems = users.slice(start, start + itemsPerPage)

  return (
    <div className="container">
      <header>
        <h1><ImUsers />Users & Their Pets</h1>
        <p>Browse users from different countries and see their beloved pets</p>
      </header>

      <section className="controls" role="region" aria-label="controls">
        <label>
          Filter by Country:
          <select value={nat} onChange={(e) => setNat(e.target.value)}>
            {countryOptions.map((opt) => (
              <option key={opt.value} value={opt.value}>
                {opt.country}
              </option>
            ))}
          </select>
        </label>

        <label>
          Number of Users:
          <input type="number" name="numOfUsers"
            min={1} max={5000}
            value={count}
            onChange={(e) => setCount(Number(e.target.value))}
          />
        </label>

        <button className="fetch-btn" onClick={handleFetch} disabled={loading || count < 1 || count > 5000}>
          <ImDownload3 /> {loading ? 'Loading…' : 'Fetch Data'}
        </button>
        <p>{count < 1 || count > 5000 ? "Select a number between 1 to 5000" : 
          (count == 1 ? "Showing 1 user" : "Showing " + count + " users")}
        </p>
      </section>

      {error && <div className="error" role="alert">{error}</div>}

      <main>
        {users.length === 0 && !loading ? <div className="placeholder">No users. Click Fetch.</div> : null}
        <div className="grid" key={page}>
          {pageItems.map(u => <UserCard key={u.id} user={u} />)}
        </div>

        {users.length > 0 && (
          <div className="pagination-controls">
            <button
              disabled={page === 1}
              onClick={() => setPage(page - 1)}
            >
              Prev
            </button>

            <span className="page-display">
              {page} / {totalPages}
            </span>

            <button
              disabled={page === totalPages}
              onClick={() => setPage(page + 1)}
            >
              Next
            </button>
          </div>
        )}
      </main>

      <footer><small>Backend endpoint: <code>{API_BASE}/api/users-with-pet</code></small></footer>
    </div>
  )
}

export default App
